# Unidoc

Generates javadoc using annotations.

## Description

This is an alternative to the standard format of documenting java source codes. Unidoc introduces the use of element-type specific annotations in code commenting. 

The annotations include:

    > @PackageDoc
    > @ClassDoc
    > @InterfaceDoc
    > @ConstructorDoc
    > @FieldDoc
    > @MethodDoc
     
      
The attributes of each annotation match the description and tags of java doc comments.
    
## Installation

## Usage

An example:

```
java doc doc comment:

/**
*Class description
*...
*
*@author David
*\
public class ExampleClass {}

```

```
Unidoc format:

@ClassDoc(description = "Class description ...", author = "David")
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
 