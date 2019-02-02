
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import core.Book;
import core.BookList;
import core.Library;
import core.MyInfoManager;

/**
 * current read adapter . the list is books that the user add to library
 */
public class current_read_adapter extends RecyclerView.Adapter<current_read_adapter.ViewHolder> {

    private List<Book> listItems;
    private Context mContext;
    private Book book;

    public current_read_adapter(List<Book> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_read_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        book = listItems.get(position);
        holder.itemView.setTag(listItems.get(position));
        holder.txtTitle.setText(book.getTitle());
        holder.Written_Name.setText(book.getUserName());
        holder.completed.setText(book.getPhase());
        holder.Chapter.setText("Chapters "+book.getnumChapters());
        holder.length1.setText(book.getLength());
        holder.Update.setText("update on "+book.getUpdaeOn());
         if (book.getImage()!=null) {
            holder.iv.setImageBitmap(book.getImage());
        }
        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Display option menu

                PopupMenu popupMenu = new PopupMenu(mContext, holder.txtOptionDigit);
                popupMenu.inflate(R.menu.options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.app_item_share:

                                try {
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                    String imageFileName = "JPEG_" + timeStamp + "_";
                                    File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                    File image = File.createTempFile(
                                            imageFileName,  /* prefix */
                                            ".jpg",         /* suffix */
                                            storageDir      /* directory */
                                    );
                                    Uri outputFileUri = Uri.fromFile(image);
                                    OutputStream fOut = new FileOutputStream(image);
                                    ;
                                    book.getImage().compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                    fOut.flush();
                                    fOut.close();

                                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                                    intent1.setType("*/*");
                                    String subject = "dsdssd";
                                    intent1.putExtra(Intent.EXTRA_SUBJECT, subject);
                                    String hi = "Hi, I recommend you to visit";
                                    String about = "dsadssdasda";
                                    intent1.putExtra(Intent.EXTRA_TEXT, hi);
                                    intent1.putExtra(Intent.EXTRA_STREAM, outputFileUri);

                                    if (intent1.resolveActivity(mContext.getPackageManager()) != null) {
                                        //   mContext.startActivity(Intent.createChooser(intent1, "dsss"));
                                        mContext.startActivity(Intent.createChooser(intent1, "Send..."));
                                    }
                                } catch (Exception e) {
                                     e.printStackTrace();

                                }
                                Toast.makeText(mContext, "share", Toast.LENGTH_LONG).show();
                                break;

                            case R.id.app_item_add_to_list:
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
                                        if(listItemsBook.contains(book)){
                                            v.findViewById(android.R.id.content);
                                            Snackbar snackbar = Snackbar
                                                    .make(v,R.string.already_added, Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                        else {
                                            MyInfoManager.getInstance().createManyToManyTable(book.getBook_id(),bl.get(position).getIdBookList());
                                            int x = Integer.parseInt(book.getVote_list())+1;

                                            book.setVote_list(String.valueOf(x));
                                            MyInfoManager.getInstance().updateBook(book);
                                            b.setCountBookList(String.valueOf(Integer.parseInt(b.getCountBookList())+1));
                                            MyInfoManager.getInstance().updateBookList(b);

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

                                notifyDataSetChanged();

                                break;

                            case R.id.app_item_copy_link:

                                Toast.makeText(mContext, "dont need to do that ", Toast.LENGTH_LONG).show();
                                break;

                            case R.id.app_item_add_to_library:

                                core.Library l= new Library(Main.userName,book.getBook_id());
                                if (MyInfoManager.getInstance().getLibrary(l)==null){
                                    MyInfoManager.getInstance().createLibrary(l);
                                    Toast.makeText(mContext, R.string.add_to_librart, Toast.LENGTH_LONG).show();
                                }
                                else{
                                    MyInfoManager.getInstance().deleteLibrary(l);
                                    Toast.makeText(mContext, R.string.remove, Toast.LENGTH_LONG).show();

                                }
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

    public void shareMathode(Book book) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            Uri outputFileUri = Uri.fromFile(image);
            OutputStream fOut = new FileOutputStream(image);
            ;
            book.getImage().compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            Intent intent1 = new Intent(Intent.ACTION_SEND);
            intent1.setType("*/*");
            String subject = "dsdssd";
            intent1.putExtra(Intent.EXTRA_SUBJECT, subject);
            String hi = "Hi, I recommend you to visit";
            String about = "dsadssdasda";
            intent1.putExtra(Intent.EXTRA_TEXT, hi);
            intent1.putExtra(Intent.EXTRA_STREAM, outputFileUri);

            if (intent1.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(Intent.createChooser(intent1, "Send..."));
            }
        } catch (Exception e) {
//            Toast.makeText(this, "sddsdssddsdsdsds",
//                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle,Update, Written_Name, Chapter, completed, length1,   txtOptionDigit;
        public ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle_current_read);
            Written_Name = (TextView) itemView.findViewById(R.id.Written_Name_current_read);
            completed = (TextView) itemView.findViewById(R.id.completed_current_read);
            Chapter = (TextView) itemView.findViewById(R.id.Chapter_current_read);
            length1 = (TextView) itemView.findViewById(R.id.length_current_read);
            iv = (ImageView) itemView.findViewById(R.id.imageViewBook_current_read);
            Update = (TextView) itemView.findViewById(R.id.Update_current_read);

            txtOptionDigit = (TextView) itemView.findViewById(R.id.txtOptionDigit_current_read);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = listItems.indexOf(v.getTag());
                    book=listItems.get(x);

                    Intent intent1 = new Intent( mContext,ChapterView.class);
                    intent1.putExtra("int",txtTitle.getText().toString());
                    intent1.putExtra("book_id", book.getBook_id()) ;

                    mContext.startActivity(intent1);


                }
            });
        }
    }
}
