SERVICES := calculator-service rounding-service load-generator

.PHONY: build $(addprefix build-,$(SERVICES)) \
        test $(addprefix test-,$(SERVICES)) \
        clean $(addprefix clean-,$(SERVICES)) \
        up down logs

build: $(addprefix build-,$(SERVICES))

$(addprefix build-,$(SERVICES)):
	cd apps/$(patsubst build-%,%,$@) && ./gradlew build

test: $(addprefix test-,$(SERVICES))

$(addprefix test-,$(SERVICES)):
	cd apps/$(patsubst test-%,%,$@) && ./gradlew test

clean: $(addprefix clean-,$(SERVICES))

$(addprefix clean-,$(SERVICES)):
	cd apps/$(patsubst clean-%,%,$@) && ./gradlew clean

up:
	docker compose up -d

upb:
	docker compose up --build

down:
	docker compose down

logs:
	docker compose logs -f
