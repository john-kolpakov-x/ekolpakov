class Asd {
  public world: string = 'World!';

  printHelloWorld() {
    console.log("hello, " + this.world);
  }
}

let asd = new Asd();
asd.printHelloWorld();
