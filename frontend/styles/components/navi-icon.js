import { componentStyle } from "../styler.js";

componentStyle(
  "vaadin-button",
  `:host(.app-bar__navi-icon),
  :host(.app-bar__context-icon),
  :host(.tab-bar__navi-icon) {
    line-height: 1;
  }

  :host(.app-bar__navi-icon) [part="label"],
  :host(.app-bar__context-icon) [part="label"],
  :host(.tab-bar__navi-icon) [part="label"] {
    display: flex;
  }
`);
