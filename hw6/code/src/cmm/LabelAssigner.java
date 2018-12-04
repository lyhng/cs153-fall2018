package cmm;

public class LabelAssigner {
  private LabelAssigner() {
  }

  private static LabelAssigner instance;

  public static LabelAssigner getInstance() {
    if (instance == null) {
      instance = new LabelAssigner();
    }

    return instance;
  }

  private int current = 1;

  public void reset() {
    this.current = 1;
  }

  public String getLabel() {
    return String.format("L%05d", this.current++);
  }
}
