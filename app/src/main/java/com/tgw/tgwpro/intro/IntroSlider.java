package com.tgw.tgwpro.intro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;
import com.tgw.tgwpro.R;
import com.tgw.tgwpro.main.MainActivity;
import com.tgw.tgwpro.register.WelcomePage;

public class IntroSlider extends AppIntro {

    // we are calling on create method
    // to generate the view for our java file.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.createInstance(
                "TGW PROMOTERS",
                "Joining the set of promoters that make up to 10k the first week of signing up",
                R.drawable.people,
                R.color.white,
                R.color.purple_200,
                R.color.white,
                0,
                0,
                R.drawable.gradient1

        ));


        addSlide(AppIntroFragment.createInstance(
                "CASH REWARDS",
                "Start by performing a task and start earning cool side cash",
                R.drawable.naira,
                R.color.white,
                R.color.purple_200,
                R.color.white,
                0,
                0,
                R.drawable.gradient2

        ));

        addSlide(AppIntroFragment.createInstance(
                "GIFT REWARDS",
                "You don't just earn money as a TGW Promoter but rewarded with gifts as well!",
                R.drawable.gift,
                R.color.white,
                R.color.purple_200,
                R.color.white,
                0,
                0,
                R.drawable.gradient3

         ));



        setTransformer(AppIntroPageTransformerType.Flow.INSTANCE);
    }

    @Override
    public void onUserRequestedPermissionsDialog() {
        super.onUserRequestedPermissionsDialog();
    }


    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        Intent intent = new Intent(IntroSlider.this, WelcomePage.class);
        startActivity(intent);
    }
}