package cli.graphcoloring;

public enum GenerateProblemAlgorithmType {
  unknown(0),
  random(1),
  wattsStrogatz(2),
  file(3);

  final int type;

  GenerateProblemAlgorithmType(int type) {
    this.type = type;
  }
}
