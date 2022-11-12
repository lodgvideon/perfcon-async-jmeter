
ifeq '$(findstring ;,$(PATH))' ';'
    detected_OS := Windows
else
    detected_OS := $(shell uname 2>/dev/null || echo Unknown)
    detected_OS := $(patsubst CYGWIN%,Cygwin,$(detected_OS))
    detected_OS := $(patsubst MSYS%,MSYS,$(detected_OS))
    detected_OS := $(patsubst MINGW%,MSYS,$(detected_OS))
endif

ifeq ($(detected_OS),Windows)
    cmd:= ./mvnw.cmd
endif
ifeq ($(detected_OS),Darwin)# Mac OS X
    cmd:= ./mvnw
endif
ifeq ($(detected_OS),Linux)
    cmd:= ./mvnw
endif
ifeq ($(detected_OS),GNU)           # Debian GNU Hurd
   cmd:= ./mvnw
endif
ifeq ($(detected_OS),GNU/kFreeBSD)  # Debian kFreeBSD
   cmd:= ./mvnw
endif
ifeq ($(detected_OS),FreeBSD)
    cmd:= ./mvnw
endif
ifeq ($(detected_OS),NetBSD)
   cmd:= ./mvnw
endif
ifeq ($(detected_OS),DragonFly)
    cmd:= ./mvnw
endif
ifeq ($(detected_OS),Haiku)
    cmd:= ./mvnw
endif


.PHONY: server
server: deps
	$(cmd) clean install spring-boot:run

.PHONY: deps
deps:
	$(cmd)  clean install

.PHONY: gui
gui: deps
	$(cmd)  clean install
	$(cmd)  -pl jmeter jmeter:configure jmeter:gui