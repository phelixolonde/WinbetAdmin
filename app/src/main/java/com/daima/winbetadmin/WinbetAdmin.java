package com.daima.winbetadmin;

import android.app.Application;
import android.content.Context;
import com.google.firebase.database.FirebaseDatabase;

public class WinbetAdmin
  extends Application
{
  protected void attachBaseContext(Context paramContext)
  {
    super.attachBaseContext(paramContext);
  }
  
  public void onCreate()
  {
    super.onCreate();
    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
  }
}


/* Location:              C:\Users\HANSEN\Documents\WinbetAdmin\classes-dex2jar.jar!\com\hansen\winbetadmin\WinbetAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */