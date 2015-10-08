package framgia.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {
    private static final int ADDMODE = 2;
    private static Button btSearchMain;
    private static Button btAddnew;
    private static ListView listView;
    private static Spinner spinnerDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFindViewById();
        //set data to spinner
        setSpinnerMain();
        //set list of department on listview and add listener
        setListViewMain();
        //set click listener on Search Button
        setSearchButtonOnClickListener();
        //set click listener on Add Button
        setAddButtonOnClickListener();
    }

    public void setFindViewById() {
        listView = (ListView) findViewById(R.id.listDivMain);
        spinnerDepartment = (Spinner) findViewById(R.id.spDepMain);
        btSearchMain = (Button) findViewById(R.id.btSearchMain);
        btAddnew = (Button) findViewById(R.id.btAdd);
    }

    private void setSpinnerMain() {
        // Creat spinner for Department list
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpiner = ArrayAdapter.createFromResource(this,
                R.array.deparmentSpinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDepartment.setAdapter(adapterSpiner);
    }

    private void setListViewMain() {
        //Creat listview of Deparments
        // Create an ArrayAdapter using the string array and a default Listview layout
        ArrayAdapter<CharSequence> adapterListview = ArrayAdapter.createFromResource(this,
                R.array.deparmentListview_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterListview.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the ListView
        listView.setAdapter(adapterListview);
        //set on Listview item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayEmployeeListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EName", parent.getAdapter().getItem(position).toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setSearchButtonOnClickListener() {
        btSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayEmployeeListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("EName", "Result");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setAddButtonOnClickListener() {
        btAddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayEmployeeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("DisplayMode", ADDMODE);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
