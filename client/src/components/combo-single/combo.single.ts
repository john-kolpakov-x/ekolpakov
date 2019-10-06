import {defineComboOption} from "./combo.commons";

class ComboSingle extends HTMLElement {
  static tagName = "combo-single";

  constructor() {
    super();
    console.log("create combo-single", this)
  }

  connectedCallback() {
    this.innerHTML = "asd";
  }

}

export function defineComboSingle() {
  defineComboOption();
  if (!customElements.get(ComboSingle.tagName)) {
    customElements.define(ComboSingle.tagName, ComboSingle);
  }
}
