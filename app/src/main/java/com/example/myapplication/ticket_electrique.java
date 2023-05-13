package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.WritableDirectElement;

import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ticket_electrique extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawar_layout_ticket ;
    private NavigationView navigation_view_ticket ;
    private ImageView menu_ticket ;
    private RadioGroup rgticke ;
    private RadioButton radioButton;
    private Button btngetTicket ;
    private   int i=0,ia=0,ib=0,ic=0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_electrique);
        drawar_layout_ticket=findViewById(R.id.drawar_layout_ticket);
        navigation_view_ticket=findViewById(R.id.navigation_view_ticket);
        menu_ticket=findViewById(R.id.menu_ticket);
        rgticke=findViewById(R.id.rgticket);
        btngetTicket=findViewById(R.id.btngetTicket);

        btngetTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioid =rgticke.getCheckedRadioButtonId();
                radioButton=findViewById(radioid);

                String nameticket =radioButton.getText().toString();

                try {
                    createpdf(nameticket);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        navigationDrawer();

        navigation_view_ticket.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addDevice:
                        startActivity(new Intent(ticket_electrique.this,principale.class));
                        drawar_layout_ticket.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ticket:
                        drawar_layout_ticket.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });



    }

    private void createpdf(String nameticket) throws FileNotFoundException {
              i++;
              String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS.toString());
               File file =new File(pdfpath, "TicketElectrique"+i+".pdf");
               pdfWriter writer = new pdfWriter(file);
               pdfDocument pdfDocument = new pdfDocument(writer);
        Document document = new Document(pdfDocument) ;
        document.setMargins(5,5,5,5);
        Drawable d =getDrawable(R.drawable.logo_login);
        Bitmap bitmap =((BitmapDrawable)d.getBitmap());
        ByteArrayInputStream stream=new ByteArrayInputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bitmapdata =stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapdata);

        Image image =new Image(imageData) ;

        Paragraph title=new Paragraph("Ticket Electrique").setFontSize(20);

        Paragraph numticket=null; ,nameticket=null;

         if(nameticket.equals("ChoixA")){
             ia++;
             numticket=new Paragraph("A0"+ia);
             nameticket=new Paragraph("ChoixA");
         }else  if(nameticket.equals("ChoixB")){
             ib++;
             numticket=new Paragraph("B0"+ib).);
             nameticket=new Paragraph("ChoixB");

         }else  if(nameticket.equals("ChoixC")){
             ib++;
             numticket=new Paragraph("C0"+ic);
             nameticket=new Paragraph("ChoixC");
         }

         float[] widht ={100f,100f};
        Table table= new Table(widht);

        DateTimeFormatter dateTimeFormatter =null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            dateTimeFormatter =DateTimeFormatter.ofPattern("dd/tt/yyyy");
        }
        table.addCell(new Cell().add(new Paragraph("Date")));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            table.addCell(new Cell().add(new Paragraph(LocalDate).now().format(dateTimeFormatter)));
        }
        DateTimeFormatter timeFormatter =null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss");
        }
      table.addCell(new Cell().add(new Paragraph("Time"));
        table.addCell(new Cell().add(new Paragraph(LocalTime).now().format(timeFormatter)));
        document.add(image);
         document.add(title);
         document.add(nameticket);
         document.add(numticket);
         document.add(table);
        document.close();
        pdfDocument.setDefaultPageSize(PageSize.A6);
        Toast.makeText(this, "Done!!!", Toast.LENGTH_SHORT).show();
    }

    private void navigationDrawer() {
        navigation_view_ticket.bringToFront();
        navigation_view_ticket.setNavigationItemSelectedListener(this);
        navigation_view_ticket.setCheckedItem(R.id.ticket);

        menu_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawar_layout_ticket.isDrawerVisible(GravityCompat.START)){
                    drawar_layout_ticket.closeDrawer(GravityCompat.START);
                }else{
                    drawar_layout_ticket.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawar_layout_ticket.isDrawerVisible(GravityCompat.START)){
            drawar_layout_ticket.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }
}