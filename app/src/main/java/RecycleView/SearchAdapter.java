package RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.book.sl.thlibarary.R;

import java.util.ArrayList;

import DataBase.Book;

public class SearchAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Book> mOriginalValues; // Original Values
    private ArrayList<Book> mDisplayedValues;    // Values to be displayed
    LayoutInflater inflater;

    public SearchAdapter(Context context, ArrayList<Book> mProductArrayList) {
        this.mOriginalValues = mProductArrayList;
        this.mDisplayedValues = mProductArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        LinearLayout llContainer;
        TextView tvName,tvPrice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row, null);
            holder.llContainer = (LinearLayout)convertView.findViewById(R.id.llContainer);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mDisplayedValues.get(position).getTitle());
        holder.tvPrice.setText(mDisplayedValues.get(position).getQuantity());

        holder.llContainer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

               // Toast.makeText(BookSearchActivity.this, mDisplayedValues.get(position).name, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Book>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Book> FilteredArrList = new ArrayList<Book>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Book>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getTitle();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Book(mOriginalValues.get(i).getId(), mOriginalValues.get(i).getTitle(), mOriginalValues.get(i).getDescription(), mOriginalValues.get(i).getAuthor(),mOriginalValues.get(i).getQuantity(),mOriginalValues.get(i).getRackNumber(),mOriginalValues.get(i).getCoverImageUrl(), mOriginalValues.get(i).getThumbnail()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}