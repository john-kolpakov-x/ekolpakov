import {defineComboOption} from "./combo.commons";

class ComboSingle extends HTMLElement {
  static tagName = "combo-single";

  constructor() {
    super();

    console.log("g423vv5 constructor :: this.close = ", this["close"]);
  }

  main: HTMLSpanElement;
  selector: HTMLSpanElement;

  connectedCallback() {

    this.main = document.createElement("span");
    this.selector = document.createElement("span");

    console.log("  this.childElementCount = ", this.childElementCount);

    var observer = new MutationObserver(function(mutations) {
      mutations.forEach(function(mutation) {
        //Detect <img> insertion
        if (mutation.addedNodes.length)
          console.info('Node added: ', mutation.addedNodes[0])
      })
    });

    observer.observe(this, { childList: true });
  }

  static get observedAttributes() {
    return ["wow"];
  }

  attributeChangedCallback(name: string, prevValue, newValue) {
    console.log("attributeChangedCallback " + name, prevValue, newValue);
  }

  attachedCallback() {
    console.log("g43v4 :: attachedCallback");
  }

  adoptedCallback() {
    console.log("542365v :: adoptedCallback");
  }

}

export function defineComboSingle() {
  defineComboOption();
  if (!customElements.get(ComboSingle.tagName)) {
    customElements.define(ComboSingle.tagName, ComboSingle);
  }
}
