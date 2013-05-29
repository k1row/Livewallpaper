package jp.k16.livewallpaper;

import android.os.Handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.view.MotionEvent;
import android.view.SurfaceHolder;

import android.content.SharedPreferences;
import android.content.res.*;

import android.service.wallpaper.WallpaperService;

import android.util.Log;


public class K16WallpaperService extends WallpaperService
{
  public K16WallpaperService ()
  {
  }

  @Override
    public void onCreate ()
    {
      super.onCreate ();
    }

  @Override
    public void onDestroy ()
    {
      super.onDestroy ();
    }

  @Override
    public Engine onCreateEngine ()
    {
      return new WallpaperEngine (getResources ());
    }

  public class WallpaperEngine extends Engine
  {
    private final Handler m_handler = new Handler ();

    private Bitmap m_image;
    private int m_px = 0;
    private int m_py = 0;
    private int m_vx = 10;
    private int m_vy = 10;

    private boolean m_visible;  // 表示状態
    private int m_width;
    private int m_height;

    private final Runnable m_draw_thread = new Runnable ()
    {
      public void run ()
      {
        draw_frame ();
      }
    };

    public WallpaperEngine (Resources res)
    {
      m_image = BitmapFactory.decodeResource (res, R.drawable.thumbnail);
    }

    @Override
      public void onCreate (SurfaceHolder surfaceHolder)
      {
        super.onCreate (surfaceHolder);
      }

    @Override
      public void onDestroy ()
      {
        super.onDestroy ();
        m_handler.removeCallbacks (m_draw_thread);
      }

    @Override
      public void onVisibilityChanged (boolean m_visible)
      {
        this.m_visible = m_visible;
        if (m_visible)
        {
          draw_frame ();
        }
        else
        {
          m_handler.removeCallbacks (m_draw_thread);
        }
      }

    @Override
      public void onSurfaceChanged (SurfaceHolder holder,
                                    int format, int width, int height)
      {
        super.onSurfaceChanged (holder, format, width, height);
        this.m_width = width;
        this.m_height= height;
        draw_frame ();
      }

    @Override
      public void onSurfaceCreated (SurfaceHolder holder)
      {
        super.onSurfaceCreated (holder);
      }

    @Override
      public void onSurfaceDestroyed (SurfaceHolder holder)
      {
        super.onSurfaceDestroyed (holder);
        m_visible = false;
        m_handler.removeCallbacks (m_draw_thread);
      }

    private void draw_frame ()
    {
      SurfaceHolder holder = getSurfaceHolder ();
      Canvas c = holder.lockCanvas ();

      c.drawColor (Color.WHITE);
      c.drawBitmap (m_image, m_px - 50, m_py - 50, null);

      holder.unlockCanvasAndPost (c);

      // 反転しておきましょか
      if (m_px < 0 || m_width < m_px)
        m_vx = -m_vx;

      if (m_py < 0 || m_height < m_py)
        m_vy = -m_vy;

      m_px += m_vx;
      m_py += m_vy;

      m_handler.removeCallbacks (m_draw_thread);
      if (m_visible)
        m_handler.postDelayed (m_draw_thread, 100);
    }
  }
}
