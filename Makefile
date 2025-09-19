BUILD_DIR := build
OUT_DIR := $(BUILD_DIR)/output
PREVIEW_DIR := $(OUT_DIR)/previews
RESUME_NAME := alysson-cirilo-resume
YAML_RESUME := data/resume.yml
MAKE_RESUME := make-resume/src/app/cli/build/libs/cli-uber.jar
IS_CI ?= false
SHORT_CIRCUIT ?= false
ifeq ($(IS_CI),true)
  GRADLE_CONSOLE_FLAG := --console=colored
else
  GRADLE_CONSOLE_FLAG :=
endif
# Top-level target
.PHONY: all
all: fancy sober markdown previews

# Phony aliases for final outputs
.PHONY: fancy sober markdown previews markupfiles
fancy: $(OUT_DIR)/alysson-cirilo-fancy-resume.pdf
sober: $(OUT_DIR)/alysson-cirilo-sober-resume.pdf
markdown: $(OUT_DIR)/alysson-cirilo-markdown-resume.md
previews: $(PREVIEW_DIR)/sober-resume-preview.png $(PREVIEW_DIR)/fancy-resume-preview.png
markupfiles: $(BUILD_DIR)/fancy/$(RESUME_NAME).tex $(BUILD_DIR)/sober/$(RESUME_NAME).tex $(BUILD_DIR)/markdown/$(RESUME_NAME).md

# Previews
$(PREVIEW_DIR)/fancy-resume-preview.png: $(OUT_DIR)/alysson-cirilo-fancy-resume.pdf | $(PREVIEW_DIR)/
	pdftoppm -r 300 -png $< > $@

$(PREVIEW_DIR)/sober-resume-preview.png: $(OUT_DIR)/alysson-cirilo-sober-resume.pdf | $(PREVIEW_DIR)/
	pdftoppm -r 300 -png $< > $@

# Resumes
$(OUT_DIR)/alysson-cirilo-fancy-resume.pdf: $(BUILD_DIR)/fancy/$(RESUME_NAME).tex | $(OUT_DIR)/
	cp -r dependencies/fancy/Awesome-CV $(BUILD_DIR)/fancy
	cd $(BUILD_DIR)/fancy && xelatex $(RESUME_NAME).tex
	mv $(patsubst %.tex,%.pdf,$<) $@

$(OUT_DIR)/alysson-cirilo-sober-resume.pdf: $(BUILD_DIR)/sober/$(RESUME_NAME).tex | $(OUT_DIR)/
	cp dependencies/sober/* $(BUILD_DIR)/sober
	cd $(BUILD_DIR)/sober && xelatex $(RESUME_NAME).tex
	mv $(patsubst %.tex,%.pdf,$<) $@

$(OUT_DIR)/alysson-cirilo-markdown-resume.md: $(BUILD_DIR)/markdown/$(RESUME_NAME).md | $(OUT_DIR)/
	cp $< $@

# Resume markup files
ifeq ($(SHORT_CIRCUIT), false)
$(BUILD_DIR)/fancy/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(BUILD_DIR)/fancy
	java -jar $(MAKE_RESUME) -f awesome -i $(YAML_RESUME) > $@

$(BUILD_DIR)/sober/$(RESUME_NAME).tex: $(MAKE_RESUME) | $(BUILD_DIR)/sober
	java -jar $(MAKE_RESUME) -f sober -i $(YAML_RESUME) > $@

$(BUILD_DIR)/markdown/$(RESUME_NAME).md: $(MAKE_RESUME) | $(BUILD_DIR)/markdown
	java -jar $(MAKE_RESUME) -f markdown -i $(YAML_RESUME) > $@
endif

# `make-resume` tool
ifeq ($(IS_CI), false)
.PHONY: $(MAKE_RESUME)
endif
$(MAKE_RESUME):
	cd make-resume && ./gradlew uberJar $(GRADLE_CONSOLE_FLAG)
# Directories
$(BUILD_DIR)/fancy:
	mkdir -p $@
$(BUILD_DIR)/sober:
	mkdir -p $@
$(BUILD_DIR)/markdown:
	mkdir -p $@

$(OUT_DIR)/:
	mkdir -p $@

$(PREVIEW_DIR)/:
	mkdir -p $@

# Clean
.PHONY: clean
clean:
	rm -rf $(BUILD_DIR)/

