# Unidoc

Generates javadoc using annotations.

## Description

This is an alternative to the standard format of documenting java source codes. Unidoc provides interfaces containing implementations(attributes) which are apt for usage by the javadoc tool in generating javadoc documentation. 

The interfaces/annotations are grouped based on declaration:

    > @ClassDoc
    > @ConstructorDoc
    > @FieldDoc
    > @MethodDoc   
    
Attributes of each annotation are internally converted to match the description and tags elements in the standard format of documenting java codes.

This project is built around these core ideas:

    * A more structured and less verbose documenting format.
    * A very beginner friendly approach to documenting java codes.
    
## Installation

## Usage

An example:

```
Standard java format:

/**
*Class description
*
*@author David
*\
public class ExampleClass {}

```

```
Unidoc format:

@ClassDoc(description = "Class description", author = "David")
public class ExampleClass {}

```  

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Also ensure to update appropriate tests.

## Authors and acknowledgement

Author: \
[Dinneya Charles](https://www.linkedin.com/in/dinneya-charles-a55801139/)

Contributors:\
[David Dinneya](https://www.linkedin.com/in/david-dinneya-aa38ba198/)

## License

[Apache License](http://www.apache.org/licenses/)
 