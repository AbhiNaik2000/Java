package com.example.vms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Other {
    private String Id, Image, FirstName, LastName, Phone, Location, Date, Time, Purpose, Reference;

    public Other() { }

    public Other(String Id, String Image, String FirstName, String LastName, String Phone, String Location, String Date, String Time, String Purpose, String Reference) {
        this.Id = Id;
        this.Image = Image;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.Location = Location;
        this.Date = Date;
        this.Time = Time;
        this.Purpose = Purpose;
        this.Reference = Reference;
    }

    public String getId() {
        return Id;
    }

    public String getImage() {
        return Image;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getLocation() {
        return Location;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getPurpose() {
        return Purpose;
    }

    public String getReference() {
        return Reference;
    }
}

class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.OtherViewHolder> implements Filterable {

    private Context context;
    private List<Other> otherList;
    private List<Other> filterList;

    public OtherAdapter(Context context, List<Other> otherList) {
        this.context = context;
        this.otherList = otherList;
        this.filterList = otherList;
    }

    @NonNull
    @Override
    public OtherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
        return new OtherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherViewHolder holder, final int position) {
        holder.tvId.setText(otherList.get(position).getId());
        Glide.with(context).load(otherList.get(position).getImage()).into(holder.imageView);
        holder.tvFirstName.setText(otherList.get(position).getFirstName());
        holder.tvLastName.setText(otherList.get(position).getLastName());
        holder.tvDate.setText(otherList.get(position).getDate());
        holder.tvTime.setText(otherList.get(position).getTime());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("firstName", otherList.get(position).getFirstName());
                intent.putExtra("lastName", otherList.get(position).getLastName());
                intent.putExtra("phone", otherList.get(position).getPhone());
                intent.putExtra("location", otherList.get(position).getLocation());
                intent.putExtra("date", otherList.get(position).getDate());
                intent.putExtra("time", otherList.get(position).getTime());
                intent.putExtra("purpose", otherList.get(position).getPurpose());
                intent.putExtra("reference", otherList.get(position).getReference());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return otherList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results=new FilterResults();
                if(constraint != null && constraint.length() > 0)
                {
                    constraint = constraint.toString().toUpperCase();
                    ArrayList<Other> filtered = new ArrayList<>();
                    for (int i=0;i<filterList.size();i++)
                    {
                        if(filterList.get(i).getFirstName().toUpperCase().contains(constraint))
                        {
                            filtered.add(filterList.get(i));
                        }
                    }
                    results.count=filtered.size();
                    results.values=filtered;
                }
                else {
                    results.count=filterList.size();
                    results.values=filterList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                otherList = (ArrayList<Other>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class OtherViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvId, tvFirstName, tvLastName, tvDate, tvTime;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.textViewId);
            imageView = itemView.findViewById(R.id.imageView);
            tvFirstName = itemView.findViewById(R.id.textViewFirstName);
            tvLastName = itemView.findViewById(R.id.textViewLastName);
            tvDate = itemView.findViewById(R.id.textViewDate);
            tvTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}