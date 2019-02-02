package com.example.shaharben_ezra.myapplication;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import core.Comment;

public class CommentListAdapter  extends ArrayAdapter<Comment> {

    private Context context;
    private List<Comment> CommentList;


    public CommentListAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        CommentList = objects;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View rootView = mInflater.inflate(R.layout.comment, null);

        TextView userName = (TextView) rootView.findViewById(R.id.txtTitle_userName);///////////user

        TextView date = (TextView) rootView.findViewById(R.id.date);

        TextView Comments = (TextView) rootView.findViewById(R.id.Comments);

        Comment Comment = getItem(position);

        Comments.setText(Comment.getDescription());
        date.setText(context.getString(R.string.on)+(CharSequence) Comment.getDate());
        userName.setText(Comment.getUserName());
        ImageView Star1 = (ImageView) rootView.findViewById(R.id.Star1);
        ImageView Star2 = (ImageView) rootView.findViewById(R.id.Star2);
        ImageView Star3 = (ImageView) rootView.findViewById(R.id.Star3);
        ImageView Star4 = (ImageView) rootView.findViewById(R.id.Star4);
        ImageView Star5 = (ImageView) rootView.findViewById(R.id.Star5);
            ImageView imageViewBook_comment = (ImageView) rootView.findViewById(R.id.imageViewBook_comment);



            switch (Comment.getrate())
            {
                case "Rate 1":
                    Star1.setVisibility(view.VISIBLE);
                    Star2.setVisibility(View.GONE);
                    Star3.setVisibility(View.GONE);
                    Star4.setVisibility(View.GONE);
                    Star5.setVisibility(View.GONE);
                    break;
                case "Rate 2":
                    Star1.setVisibility(view.VISIBLE);
                    Star2.setVisibility(View.VISIBLE);
                    Star3.setVisibility(View.GONE);
                    Star4.setVisibility(View.GONE);
                    Star5.setVisibility(View.GONE);
                    break;

                case "Rate 3":
                    Star1.setVisibility(view.VISIBLE);
                    Star2.setVisibility(View.VISIBLE);
                    Star3.setVisibility(View.VISIBLE);
                    Star4.setVisibility(View.GONE);
                    Star5.setVisibility(View.GONE);
                    break;

                case "Rate 4":
                    Star1.setVisibility(view.VISIBLE);
                    Star2.setVisibility(View.VISIBLE);
                    Star3.setVisibility(View.VISIBLE);
                    Star4.setVisibility(View.VISIBLE);
                    Star5.setVisibility(View.GONE);
                    break;

                case "Rate 5":
                    Star1.setVisibility(view.VISIBLE);
                    Star2.setVisibility(View.VISIBLE);
                    Star3.setVisibility(View.VISIBLE);
                    Star4.setVisibility(View.VISIBLE);
                    Star5.setVisibility(View.VISIBLE);
                    break;

            }

         return rootView;
    }

    @Override
    public Comment getItem(int position) {
        return CommentList.get(position);
    }

    @Override
    public int getCount() {
        return CommentList.size();
    }
}
