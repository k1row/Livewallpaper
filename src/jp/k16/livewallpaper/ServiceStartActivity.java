package jp.k16.livewallpaper;

import java.io.IOException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.app.WallpaperInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceStartActivity extends Activity implements OnClickListener
{
  private WallpaperManager m_wm;
  private Button m_button = null;

  @Override
    protected void onCreate (Bundle savedInstanceState)
    {
      super.onCreate (savedInstanceState);
      setContentView (R.layout.main);
      m_button = (Button)findViewById (R.id.service_start_button);
      m_button.setOnClickListener (this);

      // WindowManager の取得
      m_wm = WallpaperManager.getInstance (this);

      // 壁紙の最小の幅，最小の高さの取得
      int width = m_wm.getDesiredMinimumWidth ();
      int height = m_wm.getDesiredMinimumHeight ();
    }

  @Override
    public void onClick (View v)
    {
      /*
      Intent intent = new Intent (ServiceStartActivity.this, K16WallpaperService.class);
      //startService (intent);

      try
      {
        m_wm.getIWallpaperManager ().setWallpaperComponent (intent.getComponent ());
      }
      catch (IOException e)
      {
        e.printStackTrace ();
      }
      */
    }
}
