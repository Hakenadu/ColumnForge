// @ts-nocheck

import {AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import ace, {Ace} from 'ace-builds';
import {PromptService} from '../services/prompt.service';

const oop = ace.require('ace/lib/oop');
const TextMode = ace.require('ace/mode/text').Mode;
const TextHighlightRules = ace.require('ace/mode/text_highlight_rules').TextHighlightRules;

const CustomHighlightRules = function () {


  /*
   * NOTICE: The current rule set is a slightly adapted excerpt from https://github.com/sujoyu/plantuml-previewer
   */
  this.$rules = {
    start: [
      {
        token: "support.function.source.wsd",
        regex: /\$\{\w+\}/
      }
    ]
  };
};

oop.inherits(CustomHighlightRules, TextHighlightRules);

const Mode = function () {
  this.HighlightRules = CustomHighlightRules;
};
oop.inherits(Mode, TextMode);

(function () {
  this.$id = 'ace/mode/column-forge';
}).call(Mode.prototype);

@Component({
  selector: 'app-prompt',
  templateUrl: './prompt.component.html',
  styleUrls: ['prompt.component.scss']
})
export class PromptComponent implements OnInit, AfterViewInit {

  private editor: Ace.Editor;

  @ViewChild('editor')
  editorRef?: ElementRef<HTMLDivElement>;

  constructor(public promptService: PromptService, private changeDetectorRef: ChangeDetectorRef) {

  }

  ngOnInit() {
    ace.config.set('basePath', '/assets/ui/');
    ace.config.set('modePath', '');
    ace.config.set('themePath', '');
  }

  ngAfterViewInit(): void {
    if (this.editorRef) {

      this.editor = ace.edit(this.editorRef.nativeElement);
      this.editor.setTheme('ace/theme/clouds_midnight');
      this.editor.setShowPrintMargin(false);
      this.editor.session.setMode(new Mode);
      this.editor.setOptions({
        enableBasicAutocompletion: true
      });

      this.editor.session.on('change', () => {
        this.promptService.prompt = this.editor.getValue();
        this.changeDetectorRef.detectChanges();
      });

      const initialPrompt = this.promptService.prompt ?? 'you are a system for translating texts into ${language} language.\nonly answer with the translation.\n\nhere is the text:\n${text}';
      this.editor.setValue(initialPrompt, 1);
    }
  }

  onResized($event: ResizeObserverEntry) {
    if (this.editor) {
      const newContainerHeight = `${$event.contentRect.height}px`;
      const newContainerWidth = `${$event.contentRect.width}px`;

      if (this.editor.container.style.height === newContainerHeight
        && this.editor.container.style.width === newContainerWidth) {
        return;
      }

      this.editor.container.style.width = newContainerWidth;
      this.editor.container.style.height = newContainerHeight;
      this.editor.resize();
    }
  }
}
