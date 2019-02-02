package com.example.shaharben_ezra.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import core.Category;
import core.MyInfoManager;

/**
 * author Shahar Ben-Ezra
 * Category Adapter will show all the books that have the same category that the user choose
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

   private List< Category> listCategory;
    private Context mContext;
    private View v;
    private Category Category;

    public CategoryAdapter(List<Category> listItems, Context mContext) {
        this.listCategory = listItems;
        this.mContext = mContext;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category , parent, false);
        return new CategoryAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final  CategoryAdapter.ViewHolder holder, final int position) {

        Category = listCategory.get(position);
        holder.itemView.setTag(listCategory.get(position));
        holder.CategoryName1.setText(Category.getCategory_name());
        int stories_count =MyInfoManager.getInstance().getAllTheBooksCategoryName(Category.getCategory_name()).size();
        holder.storiesCount.setText(String.valueOf(stories_count)+" stories");
     }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView CategoryName1, storiesCount ;

        public ViewHolder(View itemView) {
            super(itemView);

             CategoryName1 = (TextView) itemView.findViewById(R.id.CategoryName1);
            storiesCount = (TextView) itemView.findViewById(R.id.storiesCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = listCategory.indexOf(v.getTag());
                    Category=listCategory.get(x);


                    Intent intent1 = new Intent( mContext,BooksByCategoryTabs.class);
                    intent1.putExtra("CategoryName", Category.getCategory_name()) ;

                    mContext.startActivity(intent1);


                }
            });
        }
    }

}

