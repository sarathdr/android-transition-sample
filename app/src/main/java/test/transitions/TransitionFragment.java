package test.transitions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.animation.AnticipateInterpolator;

import test.infomedia.R;

/**
 * Fragment to handle the transition
 *
 * @author sarath on 20/09/15.
 */
public class TransitionFragment extends Fragment implements View.OnClickListener {

    private Scene mScene1;
    private Scene mScene2;

    private TransitionManager mTransitionManager;
    private ViewGroup mSceneRoot;

    public static TransitionFragment newInstance() {
        return new TransitionFragment();
    }

    public TransitionFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transition, container, false);

        mSceneRoot = (ViewGroup) view.findViewById(R.id.scene_root);

        // Scene-1 : With the start button
        mScene1 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene1, getActivity());
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene2, getActivity());

        // All scene2 transitions are specified on the transition set xml file
        // The animation is based on the time delays configured for each transition
        // Look at transition/scene2_transitions.xml
        mTransitionManager = TransitionInflater.from(getActivity())
                .inflateTransitionManager(R.transition.scene2_transition_manager, mSceneRoot);

        view.findViewById(R.id.button_start).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_start) {

            final ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(500);
            changeBounds.setInterpolator(new AnticipateInterpolator());

            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(changeBounds);
            transitionSet.addListener(new TransitionListenerSceneOne() {
                @Override
                public void onTransitionEnd(Transition transition) {

                    // Start scene2 transition after scene1
                    mTransitionManager.transitionTo(mScene2);
                }
            });

            TransitionManager.go(mScene1, transitionSet);
        }
    }

    private abstract class TransitionListenerSceneOne implements Transition.TransitionListener {

        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    }


}
