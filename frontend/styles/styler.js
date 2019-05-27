import "@polymer/polymer/lib/elements/custom-style.js";
import "@polymer/polymer/lib/elements/dom-module.js";

export function style(css) {
  styleInclude(undefined, css);
}

export function styleInclude(include, css) {
  const customStyleElement = document.createElement("custom-style");
  const styleElement = document.createElement("style");
  if (include) {
    styleElement.setAttribute("include", include);
  }

  styleElement.innerHTML = css;

  customStyleElement.appendChild(styleElement);
  document.head.appendChild(customStyleElement);
}

let id = 0;
export function componentStyle(tagAndInclude, css) {
  const tag = typeof tagAndInclude == "string" ? tagAndInclude : tagAndInclude.tag;
  const include = tagAndInclude.include;

  const domModuleElement = document.createElement("dom-module");
  domModuleElement.setAttribute("theme-for", tag);
  domModuleElement.id = "component-theme-" + id++;
  const templateElement = document.createElement("template");
  domModuleElement.appendChild(templateElement);

  const styleElement = document.createElement("style");
  if (include) {
    styleElement.setAttribute("include", include);
  }
  styleElement.innerHTML = css;

  templateElement.content.appendChild(styleElement);

  document.head.appendChild(domModuleElement);
}
export function globalStylesheet(url) {
  const linkElement = document.createElement("link");
  linkElement.rel = "stylesheet";
  linkElement.href = url;

  document.head.appendChild(linkElement);
}
