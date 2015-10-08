package framgia.employeemanagement;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class EmployeeAdapter extends BaseAdapter{
    private static final int VIEWMODE = 1;
    private static final int ADDMODE = 2;
    private static final int EDITMODE = 3;
    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Employee employee=null;
    /*************  EmployeeAdapter Constructor *****************/
    public EmployeeAdapter(Activity a, ArrayList d,Resources resLocal) {
        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /******** What is the size of Passed Arraylist Size ************/
    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView text;
        public TextView text1;
        public ImageView image;
    }
    /****** Depends upon data size called for each row , Create each Employee row *****/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if(convertView==null){
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.employee_listview, null);
            /****** View Holder Object to contain tabitem.xml file elements ******/
            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.text);
            holder.text1=(TextView)vi.findViewById(R.id.text1);
            holder.image=(ImageView)vi.findViewById(R.id.image);
            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();
        if(data.size()<=0)
        {
            holder.text.setText("No Data");
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            employee=null;
            employee = ( Employee ) data.get( position );

            /************  Set Model values in Holder elements ***********/
            //Display several data only
            holder.text.setText(employee.getName());
            holder.text1.setText(employee.getPosition() + " /Leave date: " + employee.getLeaveDate());
            holder.image.setImageResource(
                    res.getIdentifier(
                            "framgia.employeemanagement:drawable/" + employee.getImage()
                            , null, null));
            vi.setBackgroundColor((employee.getLeaveDate() != "") ?
                    Color.argb(0x80, 0x80, 0x80, 0x80) : Color.argb(0, 0, 0, 0));
            /******** Set Item Click Listner for LayoutInflater for each row *******/
            vi.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Put up the Yes/No message box
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder
                            .setMessage("Do you want to edit this employee?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Yes button clicked, do something
                                    Intent intent = new Intent(activity,DisplayEmployeeDetailActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("DisplayMode",EDITMODE);
                                    intent.putExtras(bundle);
                                    activity.startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", null)						//Do nothing on no
                            .show();
                    return true;
                }
            });
            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity,DisplayEmployeeDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("DisplayMode", VIEWMODE);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
            }
        });
        }
        return vi;
    }
}