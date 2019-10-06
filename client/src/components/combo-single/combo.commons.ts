class ComboOption extends HTMLElement {
  static tagName = "combo-option";
}

export function defineComboOption() {
  if (!customElements.get(ComboOption.tagName)) {
    customElements.define(ComboOption.tagName, ComboOption);
  }
}
