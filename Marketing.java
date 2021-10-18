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

public class Marketing {
    private String Id, Image, FirstName, LastName, Phone, Location, Date, Time, CompanyName, Purpose, Reference, Document;

    public Marketing() { }

    public Marketing(String Id, String Image, String FirstName, String LastName, String Phone, String Location, String Date, String Time, String CompanyName, String Purpose, String Reference, String Document) {
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
        this.Document = Document;
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

    public String getDocument() {
        return Document;
    }
}

class MarketingAdapter extends RecyclerView.Adapter<MarketingAdapter.MarketingViewHolder> implements Filterable {

    private Context context;
    private List<Marketing> marketingList;
    private List<Marketing> filterList;

    public MarketingAdapter(Context context, List<Marketing> marketingList) {
        this.context = context;
        this.marketingList = marketingList;
        this.filterList = marketingList;
    }

    @NonNull
    @Override
    public MarketingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
        return new MarketingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketingViewHolder holder, final int position) {
        holder.tvId.setText(marketingList.get(position).getId());
        Glide.with(context).load(marketingList.get(position).getImage()).into(holder.imageView);
        holder.tvFirstName.setText(marketingList.get(position).getFirstName());
        holder.tvLastName.setText(marketingList.get(position).getLastName());
        holder.tvDate.setText(marketingList.get(position).getDate());
        holder.tvTime.setText(marketingList.get(position).getTime());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("firstName", marketingList.get(position).getFirstName());
                intent.putExtra("lastName", marketingList.get(position).getLastName());
                intent.putExtra("phone", marketingList.get(position).getPhone());
                intent.putExtra("location", marketingList.get(position).getLocation());
                intent.putExtra("date", marketingList.get(position).getDate());
                intent.putExtra("time", marketingList.get(position).getTime());
                intent.putExtra("companyName", marketingList.get(position).getCompanyName());
                intent.putExtra("purpose", marketingList.get(position).getPurpose());
                intent.putExtra("reference", marketingList.get(position).getReference());
                intent.putExtra("document", marketingList.get(position).getDocument());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return marketingList.size();
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
                    ArrayList<Marketing> filtered = new ArrayList<>();
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
                marketingList = (ArrayList<Marketing>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MarketingViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvId, tvFirstName, tvLastName, tvDate, tvTime;

        public MarketingViewHolder(@NonNull View itemView) {
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