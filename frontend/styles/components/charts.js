import "@vaadin/vaadin-charts/theme/vaadin-chart-default-theme";
import { componentStyle } from "../styler.js";

componentStyle(
  { tag: "vaadin-chart", include: "vaadin-chart-default-theme" },
  `
    .highcharts-axis-title {
      fill: var(--lumo-secondary-text-color);
    }

    .highcharts-axis-labels {
      fill: var(--lumo-tertiary-text-color);
    }

    .highcharts-axis-line,
    .highcharts-tick {
      stroke: var(--lumo-contrast-30pct);
    }

    .highcharts-area {
      fill-opacity: 0.5;
    }

    :host(.pending) .highcharts-color-0,
    :host(.outstanding) .highcharts-color-0 {
      fill: rgb(255, 194, 122);
    }

    :host(.confirmed) .highcharts-color-0,
    :host(.paid) .highcharts-color-0 {
      fill: var(--lumo-success-text-color);
    }

    :host(.submitted) .highcharts-color-0,
    :host(.open) .highcharts-color-0 {
      fill: var(--lumo-primary-text-color);
    }

    :host(.failed) .highcharts-color-0,
    :host(.overdue) .highcharts-color-0 {
      fill: var(--lumo-error-text-color);
    }

    .highcharts-background {
      fill: transparent;
    }

    .highcharts-container {
      font-family: inherit;
    }

    .highcharts-data-label {
      font-size: inherit;
      font-weight: inherit;
    }

    .highcharts-yaxis-grid  {
      display: none;
    }
  `
);
