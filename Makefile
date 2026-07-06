SERVICES := calculator-service rounding-service load-generator

.PHONY: build $(addprefix build-,$(SERVICES)) \
        test $(addprefix test-,$(SERVICES)) \
        clean $(addprefix clean-,$(SERVICES)) \
        up down logs

build: $(addprefix build-,$(SERVICES))

$(addprefix build-,$(SERVICES)):
	@printf "\033[31mBuilding %s...\033[0m\n" "$(patsubst build-%,%,$@)"
	cd apps/$(patsubst build-%,%,$@) && ./gradlew build

test: $(addprefix test-,$(SERVICES))

$(addprefix test-,$(SERVICES)):
	@printf "\033[31mTesting %s...\033[0m\n" "$(patsubst test-%,%,$@)"
	cd apps/$(patsubst test-%,%,$@) && ./gradlew test

clean: $(addprefix clean-,$(SERVICES))

$(addprefix clean-,$(SERVICES)):
	@printf "\033[31mCleaning %s...\033[0m\n" "$(patsubst clean-%,%,$@)"
	cd apps/$(patsubst clean-%,%,$@) && ./gradlew clean

up:
	docker compose up -d

upb:
	docker compose up --build

down:
	docker compose down

logs:
	docker compose logs -f
