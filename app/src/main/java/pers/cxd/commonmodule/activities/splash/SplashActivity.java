package pers.cxd.commonmodule.activities.splash;

import pers.cxd.commonmodule.R;
import pers.cxd.mvplibrary.MvpAct;

public class SplashActivity extends MvpAct<SplashContract.Presenter, SplashContract.Model>
        implements SplashContract.View {

    @Override
    protected SplashContract.Presenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected SplashContract.Model createModel() {
        return new SplashModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    protected void setUp() {
        mPresenter.getSomeData("1", "2");
    }

    @Override
    public void onSomeDataGotten(Object data) {

    }
}
