package cli.graphcoloring;

public enum GenerateProblemType {
  unknown(0),
  random(1),
  wattsStrogatz(2),
  file(3);

  final int type;

  GenerateProblemType(int type) {
    this.type = type;
  }
}
