import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'linkify'
})
export class LinkifyPipe implements PipeTransform {

  constructor(private domSanitizer: DomSanitizer) {}

  private static stylize(text: string): string {
    let stylizedText = '';
    if (text && text.length > 0) {
      for (const t of text.split(' ')) {
        if ( t.startsWith('https://')) {
          stylizedText += `<a class="in-message-link" href="${t}">${t}</a> `;
        } else if (t.startsWith('http://')) {
          const link = t.slice(7);
          stylizedText += `<a class="in-message-link" href="https://${link}">${t}</a> `;
        } else if (t.startsWith('www.')) {
          stylizedText += `<a class="in-message-link" href="https://${t}">${t}</a>`;
        } else {
          stylizedText += t + ' ';
        }
      }
      return stylizedText;
    } else { return text; }
  }

  transform(value: any, args?: any): any {
    return this.domSanitizer.bypassSecurityTrustHtml(LinkifyPipe.stylize(value));
  }

}
