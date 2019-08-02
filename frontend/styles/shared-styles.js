import { styleInclude, globalStylesheet } from "./styler.js";

import "@vaadin/vaadin-lumo-styles/badge";
import "@vaadin/vaadin-lumo-styles/color";
import "@vaadin/vaadin-lumo-styles/style";
import "@vaadin/vaadin-lumo-styles/typography";

import "./components/account-switcher.js";
import "./components/app-bar.js";
import "./components/brand-expression.js";
import "./components/button.js";
import "./components/charts.js";
import "./components/details-drawer.js";
import "./components/floating-action-button";
import "./components/initials.js";
import "./components/list-item.js";
import "./components/navi-drawer.js";
import "./components/navi-icon.js";
import "./components/navi-item.js";
import "./components/navi-menu.js";
import "./components/tab-bar.js";

globalStylesheet("styles/lumo-border-radius.css");
globalStylesheet("styles/lumo-icon-size.css");
globalStylesheet("styles/lumo-margin.css");
globalStylesheet("styles/lumo-padding.css");
globalStylesheet("styles/lumo-shadow.css");
globalStylesheet("styles/lumo-spacing.css");
globalStylesheet("styles/lumo-typography.css");

globalStylesheet("styles/misc/box-shadow-borders.css");

globalStylesheet("styles/views/dashboard.css");
globalStylesheet("styles/views/grid-view.css");
globalStylesheet("styles/views/report-details.css");
globalStylesheet("styles/views/view-frame.css");

styleInclude(
  "lumo-badge",
  `:root {
    --navi-drawer-rail-width: calc(var(--lumo-size-m) * 1.75);
    --navi-drawer-width: calc(var(--lumo-size-m) * 7);
    --navi-item-indentation: calc(var(--lumo-icon-size-s) + var(--lumo-space-l));

    --details-drawer-width: calc(var(--lumo-size-m) * 11);

    --transition-duration-s: 160ms;
    --transition-duration-m: 240ms;
    --transition-duration-l: 320ms;
  }

  /* Responsive sizing and spacing */
  @media (max-width: 479px) {
    :root {
      --lumo-space-r-m: var(--lumo-space-s);
      --lumo-space-r-l: var(--lumo-space-m);
      --lumo-space-r-x: 0;

      --lumo-space-wide-r-m: var(--lumo-space-wide-s);
      --lumo-space-wide-r-l: var(--lumo-space-wide-m);

      --app-bar-height: var(--lumo-size-l)
    }
  }
  @media (min-width: 480px) and (max-width: 1023px) {
    :root {
      --lumo-space-r-m: var(--lumo-space-s);
      --lumo-space-r-l: var(--lumo-space-m);
      --lumo-space-r-x: var(--lumo-space-m);

      --lumo-space-wide-r-m: var(--lumo-space-wide-s);
      --lumo-space-wide-r-l: var(--lumo-space-wide-m);

      --app-bar-height: var(--lumo-size-l)
    }
  }
  @media (min-width: 1024px) {
    :root {
      --lumo-space-r-m: var(--lumo-space-m);
      --lumo-space-r-l: var(--lumo-space-l);
      --lumo-space-r-x: var(--lumo-space-l);

      --lumo-space-wide-r-m: var(--lumo-space-wide-m);
      --lumo-space-wide-r-l: var(--lumo-space-wide-l);

      --app-bar-height: var(--lumo-size-xl);
    }
  }

  html,
  body {
    height: 100%;
    overflow: hidden;
    width: 100%;
  }

  .root {
    background-color: var(--lumo-contrast-5pct);
  }

  .app-header-outer,
  .app-footer-outer {
    z-index: 3;
  }

  vaadin-grid-cell-content {
    text-overflow: ellipsis;
  }

  vaadin-text-field {
    align-self: auto;
  }
`);
