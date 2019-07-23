import { componentStyle } from "../styler.js";

componentStyle(
  { tag: "vaadin-button" },
  `
    :host([theme~="tertiary-inline"][theme~="icon"]) {
      min-width: 0;
      padding: 0;
    }
  `
);
