# Style Guide

EvacSim uses the [PEP 8](https://www.python.org/dev/peps/pep-0008/) style guide
and uses [PyLint](https://pypi.org/project/pylint/) to enforce it, with a few
additions and exceptions:

- No maximum line length
- No requirement for module docstrings
- No minimum number of public methods in a class
- No maximum number of method arguments
- Use single quotes instead of double quotes for strings
- Use f-strings to format strings whenever possible

Be sure to use PyLint to check adherence to this style guide:

```
pylint evacsim/
```

