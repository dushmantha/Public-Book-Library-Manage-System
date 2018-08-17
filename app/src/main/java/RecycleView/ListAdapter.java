package RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.book.sl.thlibarary.BookViewOrEditActivity;
import com.book.sl.thlibarary.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import DataBase.Cart;

public class ListAdapter extends BaseAdapter {

    private ArrayList<Cart> mOriginalValues; // Original Values
    private ArrayList<Cart> mDisplayedValues;    // Values to be displayed
    LayoutInflater inflater;

    public ListAdapter(Context context, ArrayList<Cart> mProductArrayList) {
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
        TextView titleName, description, status, username;
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ListAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.book_cell, null);
            holder.llContainer = (LinearLayout) convertView.findViewById(R.id.row_id);
            holder.titleName = (TextView) convertView.findViewById(R.id.title_name);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ListAdapter.ViewHolder) convertView.getTag();
        }
        holder.titleName.setText(mDisplayedValues.get(position).getBook().getTitle());
        holder.description.setText(mDisplayedValues.get(position).getBook().getDescription());
        String status;
        if (mDisplayedValues.get(position).isAdminAccept()){
             status = "ACCEPT";
        } else {
            status = "WAITING FOR ACCEPT";
        }
        holder.status.setText(status);
        holder.username.setText(mDisplayedValues.get(position).getUser().getUserName());
        Glide.with(inflater.getContext()).load(mDisplayedValues.get(position).getBook().getThumbnail()).into(holder.imageView);
        final ViewHolder finalHolder = holder;
        holder.llContainer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (mDisplayedValues.get(position).getUser().isAdministrator()){
                    finalHolder.status.setText("ACCEPT");
                } else {
                    Cart book = (Cart) mDisplayedValues.get(position);
                    Intent intent = new Intent(inflater.getContext(), BookViewOrEditActivity.class);
                    intent.putExtra("book", mDisplayedValues.get(position).getBook());
                    inflater.getContext().startActivity(intent);
                }
            }
        });

        return convertView;
    }
}