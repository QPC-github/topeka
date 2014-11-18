/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.samples.apps.topeka.widget.quiz;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.samples.apps.topeka.model.Category;
import com.google.samples.apps.topeka.model.quiz.ToggleTranslateQuiz;

import java.util.ArrayList;

public class ToggleTranslateQuizView extends AbsQuizView<ToggleTranslateQuiz> {

    private LayoutParams mOptionsParams;
    private boolean[] mAnswers;

    public ToggleTranslateQuizView(Context context, Category category, ToggleTranslateQuiz quiz) {
        super(context, category, quiz);
        mAnswers = new boolean[quiz.getOptions().length];
    }

    @Override
    protected View getQuizContentView() {
        if (null == mOptionsParams) {
            mOptionsParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        String[][] options = getQuiz().getOptions();
        for (int i = 0; i < options.length; i++) {
            layout.addView(getOptionsView(options[i], i), mOptionsParams);
        }
        return layout;
    }

    @Override
    protected boolean isAnswerCorrect() {
        return getQuiz().isAnswerCorrect(getCheckedAnswers());
    }

    private Button getOptionsView(String[] options, int optionId) {
        if (null == options || options.length != 2) {
            throw new IllegalArgumentException("The options provided were invalid: " + options);
        }
        Button button = new Button(getContext());
        button.setText(options[0] + " <> " + options[1]);
        button.setOnClickListener(this);
        button.setTag(optionId);
        return button;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Object tag = v.getTag();
        if (null != tag) {
            Integer answerId = (Integer) tag;
            toggleAnswerFor(answerId);
            allowAnswer();
        }
    }

    private void toggleAnswerFor(int answerId) {
        mAnswers[answerId] = !mAnswers[answerId];
    }

    private int[] getCheckedAnswers() {
        ArrayList<Integer> answers = new ArrayList<Integer>();
        for (int i = 0; i < mAnswers.length; i++) {
            if (mAnswers[i]) {
                answers.add(i);
            }
        }
        if (!answers.isEmpty()) {
            //manual int extraction to avoid boxing issues
            final int answersSize = answers.size();
            int[] answersArray = new int[answersSize];
            for (int i = 0; i < answersSize; i++) {
                answersArray[i] = answers.get(i);
            }
            return answersArray;
        }

        return null;
    }
}