package com.example.shaharben_ezra.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import core.BookList;
import core.MyInfoManager;

    public class reading_list_adapter extends RecyclerView.Adapter<  reading_list_adapter.ViewHolder> {

        private List<BookList> listItems;
        private Context mContext;
        private View v;
        private BookList BookList;
        public reading_list_adapter(List<BookList> listItems, Context mContext) {
            this.listItems = listItems;
            this.mContext = mContext;
        }

        @Override
        public   reading_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_list, parent, false);
            return new com.example.shaharben_ezra.myapplication.reading_list_adapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            BookList = listItems.get(position);
            holder.itemView.setTag(listItems.get(position));
            holder.txtTitle.setText(BookList.getBookListName());
            holder.extratxt.setText("stories "+ BookList.getCountBookList());
            holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( final View v) {
                    //Display option menu

                    PopupMenu popupMenu = new PopupMenu(mContext , holder.txtOptionDigit);
                    popupMenu.inflate(R.menu.options_reading_list);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            BookList=listItems.get(position);
                            switch (item.getItemId()) {
                                case R.id.delete_menu:
                                    //Delete item
                                    MyInfoManager.getInstance().deleteListbook(BookList);
                                    v.findViewById(android.R.id.content);
                                    Snackbar snackbar = Snackbar
                                            .make(v,R.string.deleted_list, Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                 //   listItems.remove(BookList);
                                    notifyDataSetChanged();

                                    break;
                                case R.id.Rename_menu:

                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    // 2. Chain together various setter methods to set the dialog characteristics
                                    builder.setMessage( R.string.dialog_mes );
                                    builder. setTitle(R.string.dialog_title);
                                    final EditText edittext = new EditText(mContext);
                                    builder.setView(edittext);
                                    edittext.setText(BookList.getBookListName());
                                    builder.setPositiveButton(  R.string.renane ,new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            BookList.setBookListName(edittext.getText().toString());
                                            MyInfoManager.getInstance().updateBookList(BookList);
                                            v.findViewById(android.R.id.content);
                                            Snackbar snackbar = Snackbar
                                                    .make(v, com.example.shaharben_ezra.myapplication.R.string.list_successfully_renamed, Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    });
                                    builder.setNegativeButton(com.example.shaharben_ezra.myapplication.R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });

                                    // 3. Get the AlertDialog from create()
                                    AlertDialog dialog = builder.create();

                                    dialog.show();
                                    notifyDataSetChanged();


                                    break;
                                default:
                                    break;
                            }
                                return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }



        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txtTitle, extratxt,txtOptionDigit;

            public ViewHolder(View itemView) {
                super(itemView);

                txtTitle = (TextView) itemView.findViewById(R.id.txtTitle_reading_list);
                extratxt = (TextView) itemView.findViewById(R.id.stories_reading_list);
                txtOptionDigit = (TextView) itemView.findViewById(R.id.txtOptionDigit_reading_list);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent( mContext,ReadingListStories.class);
                        int x = listItems.indexOf((core.BookList) v.getTag());
                        BookList=listItems.get(x);
                        intent1.putExtra("int",BookList.getIdBookList()) ;
                        intent1.putExtra("name", BookList.getBookListName()) ;
                        mContext.startActivity(intent1);

                        Toast.makeText(v.getContext(), "dssddsds", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }


    }
