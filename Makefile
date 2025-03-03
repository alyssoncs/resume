BUILD_DIR=build
OUT_DIR=$(BUILD_DIR)/output
PREVIEW_DIR=previews
PREVIEW_DIR_zz=$(OUT_DIR)/previews
RESUME_NAME=alysson-cirilo-resume
YAML_RESUME=data/resume.yml
MAKE_RESUME=make-resume/src/app/cli/build/libs/cli-uber.jar

.PHONY: all
all: fancy sober markdown

.PHONY: fancy
fancy: $(OUT_DIR)/alysson-cirilo-fancy-resume.pdf

.PHONY: sober
sober: $(OUT_DIR)/alysson-cirilo-sober-resume.pdf

.PHONY: markdown
markdown: $(OUT_DIR)/alysson-cirilo-markdown-resume.md

.PHONY: previews
previews: $(PREVIEW_DIR)/sober-resume-preview.png $(PREVIEW_DIR)/fancy-resume-preview.png $(PREVIEW_DIR)/markdown-resume-preview.md

.PHONY: markupfiles
markupfiles: $(BUILD_DIR)/fancy/$(RESUME_NAME).tex $(BUILD_DIR)/sober/$(RESUME_NAME).tex $(BUILD_DIR)/markdown/$(RESUME_NAME).md
	cp -r dependencies/fancy/Awesome-CV $(BUILD_DIR)/fancy
	cp dependencies/sober/* $(BUILD_DIR)/sober

.PHONY: pdfs
pdfs: | $(OUT_DIR)
	cd $(BUILD_DIR)/fancy && xelatex $(RESUME_NAME).tex && cp $(RESUME_NAME).pdf ../../$(OUT_DIR)/alysson-cirilo-fancy-resume.pdf
	cd ../..
	cd $(BUILD_DIR)/sober && xelatex $(RESUME_NAME).tex && cp $(RESUME_NAME).pdf ../../$(OUT_DIR)/alysson-cirilo-sober-resume.pdf
	cd ../..
	cp $(BUILD_DIR)/markdown/$(RESUME_NAME).md $(OUT_DIR)/alysson-cirilo-markdown-resume.md

.PHONY: fastoutput
fastoutput: pdfs | $(PREVIEW_DIR_zz)/
	pdftoppm -r 300 -png $(OUT_DIR)/alysson-cirilo-fancy-resume.pdf > $(PREVIEW_DIR_zz)/fancy-resume-preview.png
	pdftoppm -r 300 -png $(OUT_DIR)/alysson-cirilo-sober-resume.pdf > $(PREVIEW_DIR_zz)/sober-resume-preview.png

$(BUILD_DIR)/fancy/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(BUILD_DIR)/fancy
	java -jar $(MAKE_RESUME) -f awesome -i $(YAML_RESUME) > $@

$(BUILD_DIR)/sober/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(BUILD_DIR)/sober
	java -jar $(MAKE_RESUME) -f sober -i $(YAML_RESUME) > $@

$(BUILD_DIR)/markdown/$(RESUME_NAME).md: $(MAKE_RESUME) | $(BUILD_DIR)/markdown
	java -jar $(MAKE_RESUME) -f markdown -i $(YAML_RESUME) > $@

$(BUILD_DIR)/fancy/$(RESUME_NAME).pdf: $(BUILD_DIR)/fancy/$(RESUME_NAME).tex
	cp -r dependencies/fancy/Awesome-CV $(BUILD_DIR)/fancy
	cd $(BUILD_DIR)/fancy && xelatex $(RESUME_NAME).tex

$(BUILD_DIR)/sober/$(RESUME_NAME).pdf: $(BUILD_DIR)/sober/$(RESUME_NAME).tex
	cp dependencies/sober/* $(BUILD_DIR)/sober
	cd $(BUILD_DIR)/sober && xelatex $(RESUME_NAME).tex

$(OUT_DIR)/alysson-cirilo-fancy-resume.pdf: $(BUILD_DIR)/fancy/$(RESUME_NAME).pdf | $(OUT_DIR)/
	cp $< $@

$(OUT_DIR)/alysson-cirilo-sober-resume.pdf: $(BUILD_DIR)/sober/$(RESUME_NAME).pdf | $(OUT_DIR)/
	cp $< $@

$(OUT_DIR)/alysson-cirilo-markdown-resume.md: $(BUILD_DIR)/markdown/$(RESUME_NAME).md | $(OUT_DIR)/
	cp $< $@

$(PREVIEW_DIR)/sober-resume-preview.png: $(BUILD_DIR)/sober/$(RESUME_NAME).pdf | $(PREVIEW_DIR)/
	pdftoppm -r 300 -png $< > $@

$(PREVIEW_DIR)/fancy-resume-preview.png: $(BUILD_DIR)/fancy/$(RESUME_NAME).pdf | $(PREVIEW_DIR)/
	pdftoppm -r 300 -png $< > $@

$(PREVIEW_DIR)/markdown-resume-preview.md: $(BUILD_DIR)/markdown/$(RESUME_NAME).md | $(PREVIEW_DIR)/
	cp $< $@

.PHONY: $(MAKE_RESUME)
$(MAKE_RESUME):
	cd make-resume && ./gradlew uberJar

$(BUILD_DIR)/fancy:
	mkdir -p $@
$(BUILD_DIR)/sober:
	mkdir -p $@
$(BUILD_DIR)/markdown:
	mkdir -p $@

$(OUT_DIR):
	mkdir -p $@

$(PREVIEW_DIR)/:
	mkdir -p $@

$(PREVIEW_DIR_zz)/:
	mkdir -p $@

.PHONY: clean
clean:
	rm -rf $(BUILD_DIR)/
