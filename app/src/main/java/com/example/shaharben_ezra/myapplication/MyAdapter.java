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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import core.Book;
import core.BookList;
import core.MyInfoManager;
import core.Library;


/**
 * author Shahar Ben-Ezra
 * adapter that show view book
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Book> listBook;
    private Context mContext;
    private  View v;
    private Book book;
    private  Boolean flag;// when its true will show a specific menu items;

    public MyAdapter(List<Book> listBook, Context mContext,boolean flag) {
        this.listBook = listBook;
        this.mContext = mContext;
        this.flag=flag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
// final Book
        book = listBook.get(position);
        holder.itemView.setTag(listBook.get(position));
        holder.txtTitle.setText(book.getTitle());
        holder.Written_Name.setText(book.getUserName());
        holder.completed.setText(book.getPhase());
        holder.Chapter.setText("Chapters "+MyInfoManager.getInstance().getAllChapterByBookId(book.getBook_id()).size());
        holder.length1.setText(String.valueOf(book.getLength()));
        holder.Update.setText("Update On "+book.getUpdaeOn());
        holder.Language.setText("Language "+book.getLanguage());
        holder.categories.setText("categories "+String.valueOf(book.getCategoryName()));
        holder.vote_comment.setText(String.valueOf(book.getVote_comment()));
        holder.vote_heart.setText(String.valueOf(book.getVote_heart()));
        holder.vote_list.setText(String.valueOf(book.getVote_list()));
        if(book.getImage()!=null) {
            holder.iv.setImageBitmap(book.getImage());
        }

        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Display option menu
                PopupMenu popupMenu = new PopupMenu(mContext,holder.txtOptionDigit);
                popupMenu.inflate(R.menu.options_menu);
                MenuItem item_add_to_list = popupMenu.getMenu().findItem(R.id.app_item_add_to_list);
                MenuItem item_add_to_library = popupMenu.getMenu().findItem(R.id.app_item_add_to_library);
                MenuItem delete = popupMenu.getMenu().findItem(R.id.delete);
                MenuItem EDIT = popupMenu.getMenu().findItem(R.id.EDIT);
                MenuItem Add_Chapter = popupMenu.getMenu().findItem(R.id.Add_Chapter);

                if(flag) {// if i used this adapter to a different fragment he will show a different menu item
                    item_add_to_list.setVisible(false);
                    item_add_to_library.setVisible(false);
                    item_add_to_list.setVisible(false);
                    delete.setVisible(true);
                    EDIT.setVisible(true);
                    Add_Chapter.setVisible(true);

                }
                else
                {

                    item_add_to_list.setVisible(true);
                    item_add_to_library.setVisible(true);
                    item_add_to_list.setVisible(true);
                    delete.setVisible(false);
                    EDIT.setVisible(false);
                    Add_Chapter.setVisible(false);                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        book= listBook.get(position);

                        switch (item.getItemId()) {
                            case R.id.app_item_share:
                                Intent shareIntent;
                                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"share.png";
                                OutputStream out = null;
                                File file=new File(path);
                                try {
                                    out = new FileOutputStream(file);
                                    book.getImage().compress(Bitmap.CompressFormat.PNG, 100, out);
                                    out.flush();
                                    out.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                path=file.getPath();
                                Uri bmpUri = Uri.parse("content://"+path);
                                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey you must read this book "+ book.getTitle());
                                shareIntent.setType("image/png");
                                mContext.startActivity(Intent.createChooser(shareIntent,"Send..."));
                                break;

                            case R.id.app_item_add_to_list:
                                /// adding this book to a list
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                 final List<BookList>bl=  MyInfoManager.getInstance().getAllListBooks(Main.userName);
                                String [] bLName = new String [bl.size()];//convert from obj to string
                                int i=0;
                                for(BookList b :bl){
                                    bLName[i]=b.getBookListName();
                                    i++;
                                }
                                Toast.makeText(mContext,book.getTitle(), Toast.LENGTH_LONG).show();
                                builder.setItems(bLName, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int position) {
                                        BookList b= bl.get(position);
                                        List<Book> listItemsBook;
                                        listItemsBook= MyInfoManager.getInstance().getAllTheBooksAtList( b.getIdBookList());
                                        if(listItemsBook.contains(book)){/// check if the story already ing this list
                                            v.findViewById(android.R.id.content);
                                            Snackbar snackbar = Snackbar
                                                    .make(v,R.string.already_added, Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                        else {
                                            MyInfoManager.getInstance().createManyToManyTable(book.getBook_id(),bl.get(position).getIdBookList());
                                            int Vote_list= Integer.parseInt(book.getVote_list())+1;// add 1 vote list
                                            book.setVote_list(String.valueOf(Vote_list));// update vote list in the book
                                            MyInfoManager.getInstance().updateBook(book);// update book at the book table
                                            b.setCountBookList(String.valueOf(Integer.parseInt(b.getCountBookList())+1));///update book list count
                                            MyInfoManager.getInstance().updateBookList(b);// update book list at book list table

                                            v .findViewById(android.R.id.content);
                                            Snackbar snackbar = Snackbar
                                                    .make(v, R.string.story_succesfully, Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                            notifyDataSetChanged();

                                        }

                                    }
                                });
                                AlertDialog dialog = builder.create();

                                dialog.show();
                                 break;
                            case R.id.app_item_copy_link:
                                Toast.makeText(mContext, R.string.dont_need, Toast.LENGTH_LONG).show();
                                break;
                            case R.id.app_item_add_to_library:
                                Library l= new Library(Main.userName,book.getBook_id());
                                  if (MyInfoManager.getInstance().getLibrary(l)==null){
                                      MyInfoManager.getInstance().createLibrary(l);
                                      Toast.makeText(mContext, R.string.add_to_librart, Toast.LENGTH_LONG).show();
                                  }
                                  else{
                                      MyInfoManager.getInstance().deleteLibrary(l);
                                      Toast.makeText(mContext, R.string.remove, Toast.LENGTH_LONG).show();

                                  }

                                break;
                            case R.id.Add_Chapter:
                                 Intent intent1 = new Intent( mContext,ChapterActivity.class);
                                intent1.putExtra("String", book.getTitle()) ;
                                intent1.putExtra("int", book.getBook_id()) ;
                                mContext.startActivity(intent1);
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
        return listBook.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle, Written_Name, Chapter, completed, length1, Language, Update, categories,
                   vote_comment, vote_heart, vote_list, txtOptionDigit;
        public ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            Written_Name = (TextView) itemView.findViewById(R.id.Written_Name);
            completed = (TextView) itemView.findViewById(R.id.completed);
            Chapter = (TextView) itemView.findViewById(R.id.Chapter);
            length1 = (TextView) itemView.findViewById(R.id.length);
            Update = (TextView) itemView.findViewById(R.id.Update);
            Language = (TextView) itemView.findViewById(R.id.Language);
            categories = (TextView) itemView.findViewById(R.id.categories);
            vote_comment = (TextView) itemView.findViewById(R.id.vote_comment);
            vote_heart = (TextView) itemView.findViewById(R.id.vote_heart);
            vote_list = (TextView) itemView.findViewById(R.id.vote_list);
            iv = (ImageView) itemView.findViewById(R.id.imageViewBook);
            txtOptionDigit = (TextView) itemView.findViewById(R.id.txtOptionDigit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     int x = listBook.indexOf(v.getTag());
                    book= listBook.get(x);

                    Intent intent1 = new Intent( mContext,ChapterView.class);
                    intent1.putExtra("int",txtTitle.getText().toString());
                    intent1.putExtra("book_id", book.getBook_id()) ;

                    mContext.startActivity(intent1);


                }
            });


        }
    }


}