package com.magi.mydemos;

import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SceneActivity extends AppCompatActivity {

    private Scene sceneOverview;
    private Scene sceneInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        ViewGroup sceneRoot = findViewById(R.id.scene_root);
        sceneOverview = Scene.getSceneForLayout(sceneRoot, R.layout.scene_overview, getBaseContext());
        sceneInfo = Scene.getSceneForLayout(sceneRoot, R.layout.scene_info, getBaseContext());

        TransitionManager.go(sceneOverview);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInfo:
                Transition transition = TransitionInflater.from(getBaseContext()).inflateTransition(R.transition.transition);
                TransitionManager.go(sceneInfo, transition);
                break;
            case R.id.btnClose:
                TransitionManager.go(sceneOverview);
                break;
        }
    }
}
