package cmm;

import java.util.HashSet;
import java.util.Set;

public class LabelAssigner {
  private LabelAssigner() {
    this.labels = new HashSet<>();
  }

  private static LabelAssigner instance;

  public static LabelAssigner getInstance() {
    if (instance == null) {
      instance = new LabelAssigner();
    }

    return instance;
  }

  private Set<String> labels;
  private int maximum = 5;

  private String random() {
    return Long.toHexString(Double.doubleToLongBits(Math.random()));
  }

  public String getLabel() {
    int n = 0;
    String label;

    while (n++ < maximum) {
      label = ("L" + this.random()).substring(0, 4);

      if (!this.labels.contains(label)) {
        this.labels.add(label);
        return label;
      }
    }

    // TODO: raise Exception
    return null;
  }
}
