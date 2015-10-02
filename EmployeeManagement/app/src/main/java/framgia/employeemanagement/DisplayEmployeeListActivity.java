package framgia.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */
public class DisplayEmployeeListActivity extends Activity{
    ListView list;
    EmployeeAdapter adapter;
    public ArrayList<Employee> employeeArr = new ArrayList<Employee>();
    public DisplayEmployeeListActivity employeeActivity = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_employee_list);
        Intent intent = getIntent();
        String Ename = intent.getStringExtra("EName");
        //Update name of departmmen
        TextView textList = (TextView) findViewById(R.id.txList);
        textList.setText(Ename);
        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();
        employeeActivity = this;
        Resources res = getResources();
        list = (ListView) findViewById(R.id.listEmployee);  // List defined in XML ( See Below )
        /**************** Create Custom Adapter *********/
        adapter = new EmployeeAdapter(employeeActivity, employeeArr, res);
        list.setAdapter(adapter);
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
