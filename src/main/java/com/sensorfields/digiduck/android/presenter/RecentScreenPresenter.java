package com.sensorfields.digiduck.android.presenter;

import com.sensorfields.android.mvp.Presenter;
import com.sensorfields.digiduck.android.model.DocumentRepository;
import com.sensorfields.digiduck.android.view.RecentScreenView;

/**
 * Not used at the moment, overkill.
 * Here for demo purposes.
 */
public class RecentScreenPresenter implements Presenter {

    public RecentScreenPresenter(RecentScreenView view, DocumentRepository documentRepository) {
    }

    public static class Factory implements Presenter.Factory {

        private final DocumentRepository documentRepository;

        public Factory(DocumentRepository documentRepository) {
            this.documentRepository = documentRepository;
        }

        public RecentScreenPresenter create(RecentScreenView view) {
            return new RecentScreenPresenter(view, documentRepository);
        }
    }
}
