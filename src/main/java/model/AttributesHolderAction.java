package model;

import language.LanguageBundle;
import lombok.Getter;

@Getter
public enum AttributesHolderAction {

    COPY(LanguageBundle.getResource("ACTION_COPY")),
    DELETE(LanguageBundle.getResource("ACTION_DELETE")),
    MOVE(LanguageBundle.getResource("ACTION_MOVE"));

    private final String action;

    private AttributesHolderAction(String action) {
        this.action = action;
    }
}
