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

public class Vendor {
    private String Id, Image, FirstName, LastName, Phone, Location, Date, Time, CompanyName, Purpose, Reference;

    public Vendor() { }

    public Vendor(String Id, String Image, String FirstName, String LastName, String Phone, String Location, String Date, String Time, String CompanyName, String Purpose, String Reference) {
        this.Id = Id;
        this.Image = Image;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Phone = Phone;
        this.Location = Location;
        this.Date = Date;
        this.Time = Time;
        this.CompanyName = CompanyName;
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

    public String getCompanyName() {
        return CompanyName;
    }

    public String getPurpose() {
        return Purpose;
    }

    public String getReference() {
        return Reference;
    }
}

class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorViewHolder> implements Filterable {

    private Context context;
    private List<Vendor> vendorList;
    private List<Vendor> filterList;

    public VendorAdapter(Context context, List<Vendor> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
        this.filterList = vendorList;
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder holder, final int position) {
        holder.tvId.setText(vendorList.get(position).getId());
        Glide.with(context).load(vendorList.get(position).getImage()).into(holder.imageView);
        holder.tvFirstName.setText(vendorList.get(position).getFirstName());
        holder.tvLastName.setText(vendorList.get(position).getLastName());
        holder.tvDate.setText(vendorList.get(position).getDate());
        holder.tvTime.setText(vendorList.get(position).getTime());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("firstName", vendorList.get(position).getFirstName());
                intent.putExtra("lastName", vendorList.get(position).getLastName());
                intent.putExtra("phone", vendorList.get(position).getPhone());
                intent.putExtra("location", vendorList.get(position).getLocation());
                intent.putExtra("date", vendorList.get(position).getDate());
                intent.putExtra("time", vendorList.get(position).getTime());
                intent.putExtra("companyName", vendorList.get(position).getCompanyName());
                intent.putExtra("purpose", vendorList.get(position).getPurpose());
                intent.putExtra("reference", vendorList.get(position).getReference());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
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
                    ArrayList<Vendor> filtered = new ArrayList<>();
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
                vendorList = (ArrayList<Vendor>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class VendorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvId, tvFirstName, tvLastName, tvDate, tvTime;

        public VendorViewHolder(@NonNull View itemView) {
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