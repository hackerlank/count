package datoh.com.count;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class CountActivity extends Activity implements View.OnClickListener
{
  private static final String KEY_TEAM1 = "team1";
  private static final String KEY_TEAM2 = "team2";

  private TextView tv1;
  private TextView tv2;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_count);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    tv1 = (TextView) findViewById(R.id.team1);
    tv2 = (TextView) findViewById(R.id.team2);

    tv1.setOnClickListener(this);
    tv2.setOnClickListener(this);

    restoreScore();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.count, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_remove1)
    {
      add(tv1, -1);
      return true;
    }
    else if (id == R.id.action_remove2)
    {
      add(tv2, -1);
      return true;
    }
    if (id == R.id.action_reset)
    {
      clear();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(final View v)
  {
    add((TextView) v, +1);
  }

  private void add(final TextView textview, final int add)
  {
    final int value = Integer.parseInt(textview.getText().toString());
    textview.setText("" + Math.max(0, value + add));
    saveScore();
  }

  private void clear()
  {
    tv1.setText("" + 0);
    tv2.setText("" + 0);
    saveScore();
  }

  private void saveScore()
  {
    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    final SharedPreferences.Editor edit = preferences.edit();
    edit.putString(KEY_TEAM1, tv1.getText().toString());
    edit.putString(KEY_TEAM2, tv2.getText().toString());
    edit.commit();
  }

  private void restoreScore()
  {
    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    tv1.setText(preferences.getString(KEY_TEAM1, "" + 0));
    tv2.setText(preferences.getString(KEY_TEAM2, "" + 0));
  }
}
