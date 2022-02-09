// Generated code from Butter Knife. Do not modify!
package com.example.aadharscanner.adapters;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.aadharscanner.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ShgListCustomAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private ShgListCustomAdapter.MyViewHolder target;

  @UiThread
  public ShgListCustomAdapter$MyViewHolder_ViewBinding(ShgListCustomAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.shgnameTv = Utils.findOptionalViewAsType(source, R.id.shgnameTv, "field 'shgnameTv'", TextView.class);
    target.totalShgMemberTv = Utils.findOptionalViewAsType(source, R.id.totalShgMemberTv, "field 'totalShgMemberTv'", TextView.class);
    target.size = Utils.findOptionalViewAsType(source, R.id.size, "field 'size'", TextView.class);
    target.shg_status = Utils.findOptionalViewAsType(source, R.id.shg_status, "field 'shg_status'", TextView.class);
    target.change_shg_statusTV = Utils.findOptionalViewAsType(source, R.id.change_shg_statusTV, "field 'change_shg_statusTV'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ShgListCustomAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.shgnameTv = null;
    target.totalShgMemberTv = null;
    target.size = null;
    target.shg_status = null;
    target.change_shg_statusTV = null;
  }
}
