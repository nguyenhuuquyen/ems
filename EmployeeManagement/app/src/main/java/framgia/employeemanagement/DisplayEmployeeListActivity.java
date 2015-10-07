package framgia.employeemanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */
public class DisplayEmployeeListActivity extends Activity{
    private static ListView list;
    private static EmployeeAdapter adapter;
    private static TextView resultList;
    private static Button buttonSearchOnList;
    private static ArrayList<Employee> employeeArr = new ArrayList<Employee>();
    private static DisplayEmployeeListActivity employeeActivity = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_employee_list);
        setFindViewById();
        //Update name of list data
        updateNameDataList();
        //set data to list
        setListData();
    }
    public void setFindViewById(){
        resultList = (TextView) findViewById(R.id.txList);
        buttonSearchOnList = (Button)findViewById(R.id.btSearchList);
    }
    /****** Function to Update name of list data *************/
    public void updateNameDataList(){
        Intent intent = getIntent();
        String Ename = intent.getStringExtra("EName");
        //Update name of departmmen
        resultList.setText(Ename);
    }
    /****** Function to set data in ArrayList *************/
    public void setListData()
    {
        for (int i = 0; i < 8; i++) {

            final Employee employee = new Employee();
            /******* Firstly take data in model object ******/
            employee.setName("Nguyen Huu Quyen  " + i);
            employee.setImage("employee00"+i);
            employee.setPosition("Position " + i);
            employee.setJoinDate("15/08/2014");
            employee.setLeaveDate("");
            if(i==3||i==5){
                employee.setLeaveDate("15/09/2015");
            }
            /******** Take Model Object in ArrayList **********/
            employeeArr.add(employee);
            employeeActivity = this;
            Resources res = getResources();
            list = (ListView) findViewById(R.id.listEmployee);  // List defined in XML ( See Below )
            /**************** Create Custom Adapter *********/
            adapter = new EmployeeAdapter(employeeActivity, employeeArr, res);
            list.setAdapter(adapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
