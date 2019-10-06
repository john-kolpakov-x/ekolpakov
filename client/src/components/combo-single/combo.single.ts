class ComboSingle extends HTMLElement {
  constructor() {
    super();


    this.innerText = "WOW";

    console.log("added combo single")

  }

}

export function registerComboSingle() {
  customElements.define("combo-single", ComboSingle);
}
