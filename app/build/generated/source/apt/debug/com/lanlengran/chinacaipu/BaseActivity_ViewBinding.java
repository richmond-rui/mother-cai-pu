// Generated code from Butter Knife. Do not modify!
package com.lanlengran.chinacaipu;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;

import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;

public class BaseActivity_ViewBinding<T extends BaseActivity> implements Unbinder {
  protected T target;

  private View view2131427422;

  @UiThread
  public BaseActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.title_back, "field 'titleBack' and method 'onClick'");
    target.titleBack = Utils.castView(view, R.id.title_back, "field 'titleBack'", TextView.class);
    view2131427422 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
    target.titleName = Utils.findRequiredViewAsType(source, R.id.title_name, "field 'titleName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.titleBack = null;
    target.titleName = null;

    view2131427422.setOnClickListener(null);
    view2131427422 = null;

    this.target = null;
  }
}
