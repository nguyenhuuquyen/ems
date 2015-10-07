package framgia.employeemanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 06/10/2015.
 */
public class DisplayEmployeeDetailActivity extends Activity {
    private static final int VIEWMODE = 1;
    private static final int ADDMODE = 2;
    private static final int EDITMODE = 3;
    private static EditText txJoinDate = null;
    private static EditText txBirthDay = null;
    private static EditText txPhone = null;
    private static EditText txLeaveDate = null;
    private static EditText txname = null;
    private static Spinner spinnerPlaceOfBirth = null;
    private static Spinner spinnerPosition = null;
    private static Spinner spinnerDepartment = null;
    private static Spinner spinnerStatus = null;
    private static Button btupdate = null;
    private static Button btCancel = null;
    private static Button btEdit = null;
    private static Button btAdd = null;
    private static Button btCancelAdd = null;
    private static ImageView avartar = null;
      ArrayAdapter<CharSequence> adapterSpinerPlaceOfBirth;
      ArrayAdapter<CharSequence> adapterSpinerDepartment;
      ArrayAdapter<CharSequence> adapterSpinerPosition;
      ArrayAdapter<CharSequence> adapterSpinerStatus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_employee);
        setFindViewById();
        Intent intent = getIntent();
        int displayMode = intent.getIntExtra("DisplayMode", VIEWMODE);
        setModeDisplay(displayMode);
        setButtonListenner();
    }
    public void setFindViewById(){
        txname = (EditText) findViewById(R.id.editName);
        txJoinDate = (EditText) findViewById(R.id.editJoinDate);
        txLeaveDate = (EditText) findViewById(R.id.editLeaveDate);
        txBirthDay = (EditText) findViewById(R.id.editBirthday);
        //Place of birth
        spinnerPlaceOfBirth = (Spinner) findViewById(R.id.spinnerPlaceOfBirth);
        adapterSpinerPlaceOfBirth = ArrayAdapter.createFromResource(this,
                R.array.place_of_birth_array, android.R.layout.simple_spinner_item);
        adapterSpinerPlaceOfBirth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlaceOfBirth.setAdapter(adapterSpinerPlaceOfBirth);
        //Position
        spinnerPosition = (Spinner) findViewById(R.id.spinnerPosition);
        adapterSpinerPosition = ArrayAdapter.createFromResource(this,
                R.array.position_array, android.R.layout.simple_spinner_item);
        adapterSpinerPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosition.setAdapter(adapterSpinerPosition);
        //Department
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDeparment);
        adapterSpinerDepartment = ArrayAdapter.createFromResource(this,
                R.array.deparmentListview_array, android.R.layout.simple_spinner_item);
        adapterSpinerDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterSpinerDepartment);
        //Status
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        adapterSpinerStatus = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapterSpinerStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterSpinerStatus);
        //Phone
        txPhone= (EditText) findViewById(R.id.editPhone);
        btupdate = (Button)findViewById(R.id.btUpdate);
        btCancel = (Button)findViewById(R.id.buttonCancelUpdate);
        btEdit = (Button)findViewById(R.id.buttonEdit);
        btAdd = (Button)findViewById(R.id.buttonAdd);
        btCancelAdd = (Button)findViewById(R.id.buttonCancelAdd);
        avartar = (ImageView) findViewById(R.id.imageAvatar);
    }
    /****** Function to set display mode *************/
    public void setModeDisplay(int mode){
        switch (mode){
            case VIEWMODE:
                displayEmployeeDetail();
                setButton(VIEWMODE);
                break;
            case ADDMODE:
                enableEditEmployee();
                setButton(ADDMODE);
                break;
            case EDITMODE:
                displayEmployeeDetail();
                enableEditEmployee();
                setButton(EDITMODE);
                break;
            default:
                break;//do nothing
        }
    }
    /****** Function to display*************/
    public void displayEmployeeDetail(){
        //create an annonymos employee
        Employee employee = new Employee();
        employee.setName("Emplpyee");
        employee.setPosition("Manager");
        employee.setBirthday("04/07/1989");
        employee.setLeaveDate("");
        employee.setJoinDate("15/09/2015");
        employee.setAddress("Đà Nẵng");
        employee.setDepartment("Education");
        employee.setPhone("0905477041");
        employee.setImage("01");
        employee.setStatus("Parttime");
        //Set name
        txname = (EditText) findViewById(R.id.editName);
        txname.setText(employee.getName());
        txname.setEnabled(false);
        //Set Place of birth
        setPlaceOfBirthSpinner(employee.getAddress());
        //Set birthday
        txBirthDay.setText(employee.getBirthday());
        txBirthDay.setEnabled(false);
        //Set Phone
        txPhone.setText(employee.getPhone());
        txPhone.setEnabled(false);
        //SetPosition
        setPositionSpinner(employee.getPosition());
        //SetDepartment
        setDepartmentSpinner(employee.getDepartment());
        //SetStatus
        setStatusSpinner(employee.getStatus());
        //SetJoinDate
        txJoinDate.setText(employee.getJoinDate());
        txJoinDate.setEnabled(false);
        //SetLeaveDate
        txLeaveDate.setText(employee.getLeaveDate());
        txLeaveDate.setEnabled(false);
        //Set avatar
        avartar.setImageResource(R.drawable.avar);
    }
    /****** Edit employee mode*************/
    public void enableEditEmployee(){
        //Set name
        txname.setEnabled(true);
        //Set Place of birth
        spinnerPlaceOfBirth.setEnabled(true);
        //Set birthday
        txBirthDay.setEnabled(true);
        txBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txBirthDay.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                    }
                };
                DatePickerDialog birthDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                        callback, mYear, mMonth, mDay);
                birthDateDialog.show();
            }
        });
        //Set Phone
        txPhone.setEnabled(true);
        //SetPosition
        spinnerPosition.setEnabled(true);
        //SetDepartment
        spinnerDepartment.setEnabled(true);
        //SetStatus
        spinnerStatus.setEnabled(true);
        //SetJoinDate
        txJoinDate.setEnabled(true);
        txJoinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                        txJoinDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                    }
                } ;
                DatePickerDialog joinDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                        callback, mYear, mMonth, mDay);
                joinDateDialog.show();
            }
        });
        //SetLeaveDate
        txLeaveDate.setEnabled(true);
        txLeaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txLeaveDate.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                    }
                };
                DatePickerDialog leaveDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                        callback, mYear, mMonth, mDay);
                leaveDateDialog.show();
            }
        });
        //Set avatar
        avartar.setImageResource(R.drawable.avar);
    }
    public void setPlaceOfBirthSpinner(String place){
        spinnerPlaceOfBirth.setSelection(adapterSpinerPlaceOfBirth.getPosition(place));
        spinnerPlaceOfBirth.setEnabled(false);
    }
    public void setDepartmentSpinner(String depart){
        spinnerDepartment.setSelection(adapterSpinerDepartment.getPosition(depart));
        spinnerDepartment.setEnabled(false);
    }
    public void setPositionSpinner(String position){
        spinnerPosition.setSelection(adapterSpinerPosition.getPosition(position));
        spinnerPosition.setEnabled(false);
    }
    public void setStatusSpinner(String status){
        spinnerStatus.setSelection(adapterSpinerStatus.getPosition(status));
        spinnerStatus.setEnabled(false);
    }
    //Set buttons invisible, according to the request, change the status
    public void setButton(int mode){
        btupdate.setVisibility(View.INVISIBLE);
        btCancel.setVisibility(View.INVISIBLE);
        btEdit.setVisibility(View.INVISIBLE);
        btAdd.setVisibility(View.INVISIBLE);
        btCancelAdd.setVisibility(View.INVISIBLE);
        switch (mode){
            case VIEWMODE:
                btEdit.setVisibility(View.VISIBLE);
                break;
            case EDITMODE:
                btCancel.setVisibility(View.VISIBLE);
                btupdate.setVisibility(View.VISIBLE);
                break;
            case ADDMODE:
                btAdd.setVisibility(View.VISIBLE);
                btCancelAdd.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }
    public void setButtonListenner(){
        btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update data
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEmployeeDetail();
                setButton(VIEWMODE);
            }
        });
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditEmployee();
                setButton(EDITMODE);
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Add new
            }
        });
        btCancelAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
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
