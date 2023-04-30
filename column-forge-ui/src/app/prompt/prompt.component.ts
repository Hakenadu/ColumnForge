// @ts-nocheck

import {AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import ace, {Ace} from 'ace-builds';
import {PromptService} from '../services/prompt.service';
import {Subscription} from 'rxjs';

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
        regex: /\$\{[\w\s]+\}/
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
export class PromptComponent implements OnInit, AfterViewInit, OnDestroy {

  private editor: Ace.Editor;

  @ViewChild('editor')
  editorRef?: ElementRef<HTMLDivElement>;

  private promptChangedSubscription?: Subscription;

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

      let lockEditorUpdate = false;

      this.editor.session.on('change', () => {
        if (!lockEditorUpdate) {
          this.promptService.prompt = this.editor.getValue();
          this.changeDetectorRef.detectChanges();
        }
      });

      this.promptChangedSubscription = this.promptService.prompt$.subscribe(prompt => {
        lockEditorUpdate = true;
        this.editor.setValue(prompt, 1);
        lockEditorUpdate = false;
      });

      this.promptService.prompt = 'you are a system for translating texts into ${language} language.\nonly answer with the translation.\n\nhere is the text:\n${text}';
      this.changeDetectorRef.detectChanges();
    }
  }

  ngOnDestroy() {
    this.promptChangedSubscription?.unsubscribe();
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
