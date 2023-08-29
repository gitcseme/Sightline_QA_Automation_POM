package legalhold.ModuleStep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleStep {

    private String stepName;
    private String element;
    private String elementRef;
    private String action;
    private String elementSelectorType;
    private String elementType;
    private int selectedIndex;

}
